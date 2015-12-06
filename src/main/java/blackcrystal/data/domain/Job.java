package blackcrystal.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Job implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String state;


	protected Job() {
	}

	public Job(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getState() {
		return this.state;
	}

	@Override
	public String toString() {
		return getName() + "," + getState();
	}
}
