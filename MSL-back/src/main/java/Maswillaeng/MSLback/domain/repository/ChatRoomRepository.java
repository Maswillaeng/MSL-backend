package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    @Query("select cr from ChatRoom cr join fetch cr.invited iv join fetch cr.owner ow where iv.id =:senderUserId and ow.id =:destinationUserId or iv.id =:destinationUserId and ow.id =:senderUserId")
    ChatRoom findByOwnerAndInvited(@Param("senderUserId") Long senderUserId,@Param("destinationUserId") Long destinationUserId);


}
