package in.eshwarnaz.binding;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoustmerEvent {

	private String name;
	private Date eventDate;
}
