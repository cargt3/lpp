/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Protocol;

import java.io.Serializable;

/**
 *
 * @author Karol
 */
public class SyncPacket implements Serializable {
    private final Serializable subPacket;
    private final int playerId;
    private final PacketId packetId;

    /**
     * @return the subPacket
     */
    public Serializable getSubPacket() {
        return subPacket;
    }

    /**
     * @return the playerId
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * @return the packetId
     */
    public PacketId getPacketId() {
        return packetId;
    }
    public enum PacketId {COORDINATES}
    public SyncPacket(Serializable subPacket, int playerId, PacketId packetId)
    {
        this.playerId = playerId;
        this.subPacket = subPacket;
        this.packetId = packetId;
    }
    
}
