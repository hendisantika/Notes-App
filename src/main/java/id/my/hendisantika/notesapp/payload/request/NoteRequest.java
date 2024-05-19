package id.my.hendisantika.notesapp.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * Project : Notes-App
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 5/20/24
 * Time: 06:52
 * To change this template use File | Settings | File Templates.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequest {

    public String title;

    public String content;
}
