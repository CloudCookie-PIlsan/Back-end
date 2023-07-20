package manito.springmanito.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import manito.springmanito.message.entity.Message;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

@Getter
public class ReceiveMessageResponseDto {
    private String contents;
    private String sendPersonUsername;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date receiveDay;

    public ReceiveMessageResponseDto(Message message) {
        this.contents = message.getContent();
        this.sendPersonUsername = message.getMessageGiver().getUsername();
        this.receiveDay = adjustTimeByHours(message.getCreatedAt(), -9);
    }

    private Date adjustTimeByHours(Date date, int hours) {
        Instant instant = date.toInstant();
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.UTC).plusHours(hours);
        return Date.from(offsetDateTime.toInstant());
    }
}

