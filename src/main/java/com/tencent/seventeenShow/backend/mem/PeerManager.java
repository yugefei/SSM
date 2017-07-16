package com.tencent.seventeenShow.backend.mem;

import com.tencent.seventeenShow.backend.model.User;
import com.tencent.seventeenShow.backend.model.UserPeer;
import org.apache.log4j.Logger;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static com.tencent.seventeenShow.backend.model.UserPeer.kRESULT_UNKNOWN;

/**
 * Created by EdwardZhou on 2017/7/15.
 * All Rights Reserved
 */
public class PeerManager {
    Logger logger = Logger.getLogger(PeerManager.class);
    private ConcurrentSkipListSet<Integer> roomIds;

    private ConcurrentLinkedQueue<User> userToPeer;

//    private ConcurrentSkipListSet<UserPeer> peers;

    private ConcurrentSkipListMap<User,WeakReference<UserPeer>> peeredUsers;

    private static PeerManager instance = new PeerManager();

    public static PeerManager g(){
        return instance;
    }

    private PeerManager(){
        roomIds = new ConcurrentSkipListSet<Integer>();
        userToPeer = new ConcurrentLinkedQueue<User>();
        peeredUsers = new ConcurrentSkipListMap<User, WeakReference<UserPeer>>();
    }

    public UserPeer getPeerResult(User user){
        if(peeredUsers.containsKey(user)) //已匹配, 返回couple
            return peeredUsers.get(user).get();

        if(userToPeer.contains(user)){ //在匹配队列中, 返回null
            return null;
        }else{
            if(userToPeer.isEmpty()){ //第一次匹配, 加入匹配队列
                userToPeer.offer(user);
                return null;
            }else{
                User u = userToPeer.poll(); //匹配队列中有用户, 返回第一个, 生成匹配对
                UserPeer peer = new UserPeer(u, user,this.generateRoomId());
                peeredUsers.put(u, new WeakReference<UserPeer>(peer));
                peeredUsers.put(user,new WeakReference<UserPeer>(peer));


                return peer;
            }
        }
    }

    public boolean setCanDelete(User user){
        if(peeredUsers.containsKey(user)){
            //已匹配, 返回couple
            peeredUsers.get(user).get().setCanDelete(user.getOpenId());
            if(peeredUsers.get(user).get().isCanDelete()){
                UserPeer peer = peeredUsers.get(user).get();
                User a = peer.getA();
                User b = peer.getB();

                this.removePeer(a);
                this.removePeer(b);
                return true;
            }else
                return false;
        }

        return false;
    }

    public void add5s(User user){
        UserPeer peer = peeredUsers.get(user).get();
        if(peer == null)
            return;

        peer.setTotalSeconds(peer.getTotalSeconds() + 5);
    }

    public void removePeer(User user){
        logger.info("======================\nremoved : " + user.getOpenId());
        userToPeer.remove(user);
//        peers.remove(peeredUsers.get(user).get());
        peeredUsers.remove(user);
    }

    public boolean clickLike(User user){
        if(peeredUsers.containsKey(user))
            return peeredUsers.get(user).get().clickLike(user.getOpenId());

        return false;
    }

    public void clickDislike(User user){
        if(peeredUsers.containsKey(user))
            peeredUsers.get(user).get().clickDislike(user.getOpenId());
    }

    public int matchResult(User user){
        if(!peeredUsers.containsKey(user))
            return kRESULT_UNKNOWN;

        UserPeer peer = peeredUsers.get(user).get();
        if(peer.matchResult(user.getOpenId()) != kRESULT_UNKNOWN){


            return peer.matchResult(user.getOpenId());
        }else{
            return kRESULT_UNKNOWN;
        }
    }

    private Integer generateRoomId(){
        Random r = new Random(System.currentTimeMillis());
        for(;;){
            Integer roomId = r.nextInt(Integer.MAX_VALUE);
            if(roomId < 0)
                continue;

            if(!roomIds.contains(roomId)){
                roomIds.add(roomId);
                return roomId;
            }
        }

    }

}
