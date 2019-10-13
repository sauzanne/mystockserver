package fr.mystocks.mystockserver.service.finance.measures;

import java.math.BigDecimal;
import java.util.List;

import fr.mystocks.mystockserver.service.finance.measures.constant.BinaryOperatorEnum;
import fr.mystocks.mystockserver.view.model.finance.measure.MeasureAlertModel;

public interface MeasureService {

	Integer createMeasureAlert(String login, String codeStockTicker, String codePlace, Integer measureId1,
			Integer MeasureId2, BigDecimal value, BinaryOperatorEnum binaryOperator, String comment);

	void cronMeasureAlert();

	List<MeasureAlertModel> getAllMeasure(String login, boolean triggered);


}
