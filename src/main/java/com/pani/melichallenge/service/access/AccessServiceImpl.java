package com.pani.melichallenge.service.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.pani.melichallenge.repository.access.AccessRepository;
import com.pani.melichallenge.web.bean.AccessBean;

@Service
public class AccessServiceImpl implements AccessService {

	private AccessRepository repo;
	private MongoTemplate mongoTemplate;
	private static final String AVERAGE = "average";
	private static final String AVERAGE_CODE = "AVG_CODE";

	@Autowired
	public AccessServiceImpl(AccessRepository repo, MongoTemplate mongoTemplate) {
		this.repo = repo;
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void storeNewCall(String code, Double distance, String country) {
		Access access = repo.findByCode(code);
		if (access != null) {
			Query query = new Query(Criteria.where("code").is(code));
			mongoTemplate.findAndModify(query, Update.update("calls", access.getCalls() + 1),
					new FindAndModifyOptions().returnNew(true).upsert(true), Access.class);
		} else {
			access = new Access();
			access.setCalls(1);
			access.setCountry(country);
			access.setCode(code);
			access.setDistance(distance);
			repo.save(access);
		}
		updateAverageDataAccess(distance);
	}

	private void updateAverageDataAccess(Double distance) {
		Access average = repo.findByCode(AVERAGE_CODE);
		if (average == null) {
			average = new Access();
			average.setCalls(1);
			average.setCountry(AVERAGE);
			average.setCode(AVERAGE_CODE);
			average.setDistance(distance);
			repo.save(average);
		} else {
			Integer calls = average.getCalls();
			Double newAverage = average.getDistance() * (calls.doubleValue() / (calls.doubleValue() + 1))
					+ distance / (calls.doubleValue() + 1);
			Query query = new Query(Criteria.where("code").is(AVERAGE_CODE));
			Update update = new Update();
			update.set("calls", calls + 1);
			update.set("distance", newAverage);
			mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true).upsert(true),
					Access.class);
		}
	}

	@Override
	public AccessBean getDistance(AccessType type) {
		switch (type) {
		case CERCANO:
			Access findTopByDistanceAsc = repo.findFirstByOrderByDistanceAsc();
			return createAccessBean(findTopByDistanceAsc, type);
		case LEJANO:
			Access findTopByDistanceDesc = repo.findFirstByOrderByDistanceDesc();
			return createAccessBean(findTopByDistanceDesc, type);
		case PROMEDIO:
			Access avg = repo.findByCode(AVERAGE_CODE);
			return createAccessBean(avg, type);
		default:
			return null;
		}
	}

	private AccessBean createAccessBean(Access findTopByDistanceAsc, AccessType type) {
		AccessBean bean = new AccessBean();
		bean.setAccess(findTopByDistanceAsc);
		bean.setType(type);
		return bean;
	}

}
