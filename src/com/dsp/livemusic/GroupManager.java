package com.dsp.livemusic;

import com.dsp.livemusic.Node;
import com.dsp.livemusic.State;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MembershipListener;
import org.jgroups.View;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Natnael Zeleke on 2/5/2016.
 */
public class GroupManager {


    public static final String GROUP_NAME = "DispatcherTest";
    private static final String channelInitializer = "src/fast.xml";
    private Map<String,Address> members = new ConcurrentHashMap<String, Address>();
    private boolean isServer = false;
    private JChannel channel;
    private String userName;
    private int membersLength;
    private View view;
    private Node node;
    private State state;
    private String serverName;

    public GroupManager(String userName){
        this.userName = userName;
    }


    public void  viewUpdate(View new_view){
        view = new_view;
        setMembersLength(view.getMembers().size());
        try {
            updateServer(view.getMembers().get(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Map.Entry<String , Address> entry : getMembers().entrySet()){
            if(!view.containsMember(entry.getValue())){
                members.remove(entry.getKey());
            }
        }
        for(int i = 0; i < view.getMembers().size();i++){
            if(!userExists(view.getMembers().get(i).toString())){
                addUser(view.getMembers().get(i).toString(),view.getMembers().get(i));
            }
        }


    }
    public boolean userExists(String userName){
       return null != members.get(userName);
    }


    public boolean addUser(String userName,Address userAddress){

        if(!userExists(userName)){
            members.put(userName,userAddress);
            return true;
        }
        return false;

    }

    public boolean removeUser(String userName){

        if(userExists(userName)){
            members.remove(userName);
            return true;
        }
        return false;
    }

    public void updateServer(String userName) throws Exception{

        setServerName(userName);
        if(getUserName().equals(userName)){
        System.out.println("You Are server!");
            setServer(true);
        }
        else {
            System.out.println("You Are Client");
            setServer(false);
        }
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Map<String, Address> getMembers() {
        return members;
    }

    public JChannel getChannel() {
        return channel;
    }


    public String getUserName() {
        return userName;
    }


    public boolean isServer() {
        return isServer;
    }


    private void setServer(boolean isServer) {
        this.isServer = isServer;
    }

    public int getMembersLength() {
        return membersLength;
    }

    public void setMembersLength(int membersLength) {
        this.membersLength = membersLength;
    }

    public void setChannel(JChannel channel) {
        this.channel = channel;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
