package com.chatting.domain.chat.presentation;

import com.chatting.domain.members.domain.MemberDetails;
import com.chatting.exception.AuthedException;
import com.chatting.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // 연결이 WebSocket 연결이라면
        if (accessor.getCommand() == StompCommand.CONNECT) {
            // 헤더 정보를 받아옴
            String token = accessor.getFirstNativeHeader("Authorization");

            // getMemberDetails는 memberDetails 정보를 받는 로직인데 토큰이 유효한지만 확인하면 된다.
            jwtProvider.getMemberDetails(token);
        }

        return message;
    }
}
