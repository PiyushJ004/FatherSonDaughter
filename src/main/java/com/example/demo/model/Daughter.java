package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModelProperty;

@Entity(name = "Daughter")
@Table(name = "daughter_Data", uniqueConstraints = {@UniqueConstraint(columnNames = {"d_PhoneNo", "d_Email"})})

public class Daughter implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "d_Name", updatable = true)
	@NotBlank(message = "Please provide daughter detail")
	@ApiModelProperty(notes = "This block is for daughter name")
	private String dName;
	
	@Column(name = "d_Sex", updatable = true)
	@NotNull(message = "Please provide daughter sexual identity")
	@ApiModelProperty(notes = "This block is for daughter sexual identity")
	private String dSex;
	
	@Column(name = "d_PhoneNo", updatable = true)
	@NotEmpty(message = "Please provide daughter mobile number")
	@Size(min = 10, max = 15, message = "Phone number must be 10 to 15 char long")
	@ApiModelProperty(notes = "This block is for daughter phone number")
	private String dPhoneNo;
	
	@Column(name = "d_Dob", updatable = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime dDob;
	
	@Column(name = "d_Age", updatable = true)
	@ApiModelProperty(notes = "This block is for daughter age")
	private Long dAge;
	
	@Email
	@Column(name = "d_Email", updatable = true)
	@ApiModelProperty(notes = "This block is for daughter email")
	private String dEmail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getdName() {
		return dName;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	public String getdSex() {
		return dSex;
	}

	public void setdSex(String dSex) {
		this.dSex = dSex;
	}

	public String getdPhoneNo() {
		return dPhoneNo;
	}

	public void setdPhoneNo(String dPhoneNo) {
		this.dPhoneNo = dPhoneNo;
	}

	public LocalDateTime getdDob() {
		return dDob;
	}

	public void setdDob(LocalDateTime dDob) {
		this.dDob = dDob;
	}

	public Long getdAge() {
		return dAge;
	}

	public void setdAge(Long dAge) {
		this.dAge = dAge;
	}

	public String getdEmail() {
		return dEmail;
	}

	public void setdEmail(String dEmail) {
		this.dEmail = dEmail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dAge == null) ? 0 : dAge.hashCode());
		result = prime * result + ((dDob == null) ? 0 : dDob.hashCode());
		result = prime * result + ((dEmail == null) ? 0 : dEmail.hashCode());
		result = prime * result + ((dName == null) ? 0 : dName.hashCode());
		result = prime * result + ((dPhoneNo == null) ? 0 : dPhoneNo.hashCode());
		result = prime * result + ((dSex == null) ? 0 : dSex.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Daughter other = (Daughter) obj;
		if (dAge == null) {
			if (other.dAge != null)
				return false;
		} else if (!dAge.equals(other.dAge))
			return false;
		if (dDob == null) {
			if (other.dDob != null)
				return false;
		} else if (!dDob.equals(other.dDob))
			return false;
		if (dEmail == null) {
			if (other.dEmail != null)
				return false;
		} else if (!dEmail.equals(other.dEmail))
			return false;
		if (dName == null) {
			if (other.dName != null)
				return false;
		} else if (!dName.equals(other.dName))
			return false;
		if (dPhoneNo == null) {
			if (other.dPhoneNo != null)
				return false;
		} else if (!dPhoneNo.equals(other.dPhoneNo))
			return false;
		if (dSex == null) {
			if (other.dSex != null)
				return false;
		} else if (!dSex.equals(other.dSex))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Daughter [id=" + id + ", dName=" + dName + ", dSex=" + dSex + ", dPhoneNo=" + dPhoneNo + ", dDob="
				+ dDob + ", dAge=" + dAge + ", dEmail=" + dEmail + "]";
	}

	public Daughter(Long id, @NotBlank(message = "Please provide daughter detail") String dName,
			@NotNull(message = "Please provide daughter sexual identity") String dSex,
			@NotEmpty(message = "Please provide daughter mobile number") @Size(min = 10, max = 15, message = "Phone number must be 10 to 15 char long") String dPhoneNo,
			LocalDateTime dDob, Long dAge, @Email String dEmail) {
		super();
		this.id = id;
		this.dName = dName;
		this.dSex = dSex;
		this.dPhoneNo = dPhoneNo;
		this.dDob = dDob;
		this.dAge = dAge;
		this.dEmail = dEmail;
	}

	public Daughter() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	

}
