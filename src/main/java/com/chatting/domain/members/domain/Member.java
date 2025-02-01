package com.chatting.domain.members.domain;

import com.chatting.domain.room.domain.RoomMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoomMember> roomMember;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;

        if(!Objects.equals(this.id, member.id)) return false;
        if(!Objects.equals(this.username, member.username)) return false;
        if(!Objects.equals(this.nickname, member.nickname)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // id만 해시값으로 계산하는 것이 효율적입니다.
    }
}
