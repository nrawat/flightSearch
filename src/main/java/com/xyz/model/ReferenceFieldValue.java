package com.xyz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReferenceFieldValue {
	private ReferenceField referenceField;
	private int fieldValueId;
	private String fieldValueName;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fieldValueId;
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
		ReferenceFieldValue other = (ReferenceFieldValue) obj;
		if (fieldValueId != other.fieldValueId)
			return false;
		return true;
	}

	@JsonIgnore
	public ReferenceField getReferenceField() {
		return referenceField;
	}
	public void setReferenceField(ReferenceField referenceField) {
		this.referenceField = referenceField;
	}

	@JsonIgnore
	public int getFieldValueId() {
		return fieldValueId;
	}
	public void setFieldValueId(int fieldValueId) {
		this.fieldValueId = fieldValueId;
	}

	@JsonProperty("value")
	public String getFieldValueName() {
		return fieldValueName;
	}
	public void setFieldValueName(String fieldValueName) {
		this.fieldValueName = fieldValueName;
	}
}
