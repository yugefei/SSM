package com.tencent.seventeenShow.backend.mem;

import com.tencent.seventeenShow.backend.model.User;
import com.tencent.seventeenShow.backend.utils.Utils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Edward on 2016/8/6.
 */
public class TokenManager {

    private Random random;
    private static boolean singleLogin = false;
    private Long lifeLong = (long)(24 * 60 * 60 * 1000); //MiliSeconds

    Logger logger = Logger.getLogger(TokenManager.class);

    public Long getLifeLong() {
        return lifeLong;
    }

    private Map<String,TokenModel<User>> map;

    private static TokenManager ourInstance = new TokenManager();

    public static TokenManager getInstance() {
        return ourInstance;
    }

    private TokenManager() {
        map = new HashMap<String, TokenModel<User>>();
        random = new Random();
        final long timeInterval = 24 * 60 * 60 * 1000; //一天

        Runnable runnable = new Runnable() {
            public void run() {
                for(;;) {
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // ------- code for task to run
                    mapCleaning();
                    // ------- ends here
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public TokenModel tokenize(User userDto){
            TokenModel<User> token = new TokenModel<User>();
            token.setExpireTime(new Date(System.currentTimeMillis() + this.getLifeLong()));
            token.setData(userDto);

            String hashBase = userDto.hashCode() + "" + this.random.nextLong();

            String tokenString = Utils.getHash(hashBase);

            token.setToken(tokenString);
            this.map.put(tokenString, token);

            return token;
    }
    public User getUser(String token){
        if(!check(token))
            return null;

        return this.map.get(token).getData();
    }

    public boolean check(String token){
        if(!this.map.containsKey(token))
            return false;

        if(this.map.get(token).getExpireTime().compareTo(new Date()) == -1 ) { //expire time 比现在时间小 说明过期
            this.map.remove(token);
            return false;
        }
        return true;
    }

    public void logout(String token){
        if(this.map.containsKey(token))
            this.map.remove(token);

    }
    private synchronized void mapCleaning(){
        List<String> expiredTokens = new ArrayList<String>();
        for(TokenModel<User> model : map.values()){
            if(model.getExpireTime().compareTo(new Date()) == -1 ) { //expire time 比现在时间小 说明过期
                expiredTokens.add(model.getToken());
            }
        }

        for (String token : expiredTokens){
            this.map.remove(token);
        }
        logger.info("Token map cleanedUp");
    }

}
