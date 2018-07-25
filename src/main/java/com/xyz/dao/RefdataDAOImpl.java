package com.xyz.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xyz.model.ReferenceField;
import com.xyz.model.ReferenceFieldValue;

public class RefdataDAOImpl implements RefdataDAO {
	private SqlSession sqlSession;

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<ReferenceField> getReferenceFields() {
		return sqlSession.selectList("Refdata.getReferenceFields");
	}

	@Override
	public List<ReferenceFieldValue> getRFVByFieldId(int fieldId) {
		return sqlSession.selectList("Refdata.getRFVByFieldId", fieldId);
	}

	@Override
	public void addRFV(ReferenceFieldValue rfv) throws Exception {
		sqlSession.insert("Refdata.insertRFV", rfv);
	}

	@Override
	public void deleteRFV(int fieldValueId) throws Exception {
		sqlSession.delete("Refdata.deleteRFV", fieldValueId);
	}
}