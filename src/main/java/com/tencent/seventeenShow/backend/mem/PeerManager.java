package com.tencent.seventeenShow.backend.mem;

import com.tencent.seventeenShow.backend.model.User;
import com.tencent.seventeenShow.backend.model.UserPeer;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by EdwardZhou on 2017/7/15.
 * All Rights Reserved
 */
public class PeerManager {
    private ConcurrentSkipListSet<Integer> roomIds;

    private ConcurrentLinkedQueue<User> userToPeer;

//    private ConcurrentSkipListSet<UserPeer> peers;

    private ConcurrentSkipListMap<User,WeakReference<UserPeer>> peeredUsers;

    private static PeerManager instance = new PeerManager();

    public PeerManager g(){
        return instance;
    }

    private PeerManager(){
        roomIds = new ConcurrentSkipListSet<Integer>();
        userToPeer = new ConcurrentLinkedQueue<User>();
        peeredUsers = new ConcurrentSkipListMap<User, WeakReference<UserPeer>>();
    }

    public UserPeer getMatchResult(User user){
        if(userToPeer.contains(user)){
            if(peeredUsers.containsKey(user)){
                return peeredUsers.get(user).get();
            }

            return null;
        }else{
            if(userToPeer.isEmpty()){
                userToPeer.offer(user);
            }
            else{
                User u = userToPeer.poll();
                UserPeer peer = new UserPeer(u, user,this.generateRoomId());
                peeredUsers.put(u, new WeakReference<>(peer));
                peeredUsers.put(user,new WeakReference<>(peer));

                return peer;
            }
            return null;
        }
    }

    public void removePeer(User user){
        userToPeer.remove(user);
//        peers.remove(peeredUsers.get(user).get());
        peeredUsers.remove(user);
    }

    private Integer generateRoomId(){
        Random r = new Random(System.currentTimeMillis());
        for(;;){
            Integer roomId = r.nextInt();
            if(!roomIds.contains(roomId)){
                roomIds.add(roomId);
                return roomId;
            }
        }

    }

}
