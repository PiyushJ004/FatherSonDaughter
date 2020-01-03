package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModelProperty;

@Entity(name = "Son")
@Table(name = "son_data", uniqueConstraints = { @UniqueConstraint(columnNames = { "s_PhoneNo", "s_Email" }) })

public class Son implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "s_Name", updatable = true)
	@NotBlank(message = "Please provide son name")
	@ApiModelProperty(notes = "This block is for son name")
	private String sNAme;

	@Column(name = "sex", updatable = true)
	@NotNull(message = "Please provide son sexual identity")
	@ApiModelProperty(notes = "This block is for son sexual identity")
	private String sex;

	@Column(name = "s_PhoneNo", updatable = true)
	@NotEmpty(message = "Please provide son mobile number")
	@Size(min = 10, max = 15, message = "Phone number must be 10 to 15 char long")
	@ApiModelProperty(notes = "This block is for son phone number")
	private String sPhoneNo;

	@Column(name = "s_Dob", updatable = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime sDob;

	@Column(name = "s_Age", updatable = true)
	@ApiModelProperty(notes = "This block is for son age")
	private Long sAge;

	@Email
	@Column(name = "s_Email", updatable = true)
	@ApiModelProperty(notes = "This block is for son email")
	private String sEmail;

	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "father_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties("son")
	private Father father_id;

	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH }, mappedBy = "son_id")
	@JsonIgnoreProperties("son_id")
	private List<Daughter> daughter = new ArrayList<Daughter>();
	
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "daughter_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties("son")
	private Daughter daughter_id;
	
	
	public Father getFather_id() {
		return father_id;
	}

	public void setFather_id(Father father_id) {
		this.father_id = father_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getsNAme() {
		return sNAme;
	}

	public void setsNAme(String sNAme) {
		this.sNAme = sNAme;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getsPhoneNo() {
		return sPhoneNo;
	}

	public void setsPhoneNo(String sPhoneNo) {
		this.sPhoneNo = sPhoneNo;
	}

	public LocalDateTime getsDob() {
		return sDob;
	}

	public void setsDob(LocalDateTime sDob) {
		this.sDob = sDob;
	}

	public Long getsAge() {
		return sAge;
	}

	public void setsAge(Long sAge) {
		this.sAge = sAge;
	}

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sAge == null) ? 0 : sAge.hashCode());
		result = prime * result + ((sDob == null) ? 0 : sDob.hashCode());
		result = prime * result + ((sEmail == null) ? 0 : sEmail.hashCode());
		result = prime * result + ((sNAme == null) ? 0 : sNAme.hashCode());
		result = prime * result + ((sPhoneNo == null) ? 0 : sPhoneNo.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Son other = (Son) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sAge == null) {
			if (other.sAge != null)
				return false;
		} else if (!sAge.equals(other.sAge))
			return false;
		if (sDob == null) {
			if (other.sDob != null)
				return false;
		} else if (!sDob.equals(other.sDob))
			return false;
		if (sEmail == null) {
			if (other.sEmail != null)
				return false;
		} else if (!sEmail.equals(other.sEmail))
			return false;
		if (sNAme == null) {
			if (other.sNAme != null)
				return false;
		} else if (!sNAme.equals(other.sNAme))
			return false;
		if (sPhoneNo == null) {
			if (other.sPhoneNo != null)
				return false;
		} else if (!sPhoneNo.equals(other.sPhoneNo))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Son [id=" + id + ", sNAme=" + sNAme + ", sex=" + sex + ", sPhoneNo=" + sPhoneNo + ", sDob=" + sDob
				+ ", sAge=" + sAge + ", sEmail=" + sEmail + "]";
	}

	public Son(Long id, @NotBlank(message = "Please provide son name") String sNAme,
			@NotNull(message = "Please provide son sexual identity") String sex,
			@NotEmpty(message = "Please provide son mobile number") @Size(min = 10, max = 15, message = "Phone number must be 10 to 15 char long") String sPhoneNo,
			LocalDateTime sDob, Long sAge, @Email String sEmail) {
		super();
		this.id = id;
		this.sNAme = sNAme;
		this.sex = sex;
		this.sPhoneNo = sPhoneNo;
		this.sDob = sDob;
		this.sAge = sAge;
		this.sEmail = sEmail;
	}

	public Son() {
		super();
		// TODO Auto-generated constructor stub
	}

}
