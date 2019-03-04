package fr.mystocks.mystockserver.service.finance.measures;

import java.math.BigDecimal;

import fr.mystocks.mystockserver.service.finance.measures.constant.BinaryOperatorEnum;

public interface MeasureService {

	Integer createMeasureAlert(String login, String codeStockTicker, String codePlace, Integer measureId1,
			Integer MeasureId2, BigDecimal value, BinaryOperatorEnum binaryOperator);

	void cronMeasureAlert();


}
