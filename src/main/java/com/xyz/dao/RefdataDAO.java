package com.xyz.dao;

import java.util.List;

import com.xyz.model.ReferenceField;
import com.xyz.model.ReferenceFieldValue;

public interface RefdataDAO {
	List<ReferenceField> getReferenceFields();
	List<ReferenceFieldValue> getRFVByFieldId(int fieldId);

	void addRFV(ReferenceFieldValue rfv) throws Exception;
	void deleteRFV(int fieldValueId) throws Exception;
}
