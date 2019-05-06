
package fr.mystocks.mystockserver.service.finance.constant;

import java.util.HashMap;
import java.util.Map;

public enum PeriodEnum {
	Q1(0, 4, 90, "common.firstquarter"), Q2(1, 4, 90, "common.secondquarter"), Q3(2, 4, 90, "common.thirdquarter"),
	Q4(3, 4, 90, "common.fourthquarter"), HY1(4, 2, 182, "common.halfyear"), HY2(5, 2, 183, "common.secondhalfyear"),
	Y(6, 1, 365, "common.year"); // on
	// met
	// un
	// poids
	// au
	// différentes périodes
	// (pour les tris) +

	private int value;

	private int divideFactor; // permet de calculer ce que représente la période par rapport à une année ex :
								// un trimestre est le 1/4 d'une année, le divide factor est de 4

	private int nbDays;

	private String keyDescription;

	public int getValue() {
		return value;
	}

	PeriodEnum(int value, int divideFactor, int nbDays, String keyDescription) {
		this.value = value;
		this.divideFactor = divideFactor;
		this.nbDays = nbDays;
		this.keyDescription = keyDescription;
	}

	public static PeriodEnum getFatherPeriod(PeriodEnum period) {
		if (period == PeriodEnum.HY1 || PeriodEnum.HY2 == period) {
			return PeriodEnum.Y;
		} else if (period == PeriodEnum.Q1 || period == PeriodEnum.Q2) {
			return PeriodEnum.HY1;
		} else if (period == PeriodEnum.Q3 || period == PeriodEnum.Q4) {
			return PeriodEnum.HY2;
		}
		return null;
	}

	/**
	 * Permet d'obtenir la liste des périodes qui précédent la periode en cours Ex :
	 * le 1 er semestre d'une année est précéde de l'année, du dernier trimestre ou
	 * du second semestre de l'année passée
	 * 
	 * @param period
	 * @return une map composée de la période précédente avec un indicateur sur
	 *         l'année : -1 = année passée, 0 = année en cours
	 */
	public static Map<PeriodEnum, Integer> getPreviousPeriod(PeriodEnum period) {
		Map<PeriodEnum, Integer> map = new HashMap<PeriodEnum, Integer>();
		if (period == PeriodEnum.Q1 || period == PeriodEnum.HY1 || period == PeriodEnum.Y) {
			map.put(PeriodEnum.Y, -1);
			map.put(PeriodEnum.HY2, -1);
			map.put(PeriodEnum.Q4, -1);
			return map;
		} else if (period == PeriodEnum.Q2) {
			map.put(PeriodEnum.Q1, 0);
			return map;
		} else if (period == PeriodEnum.Q3 || period == PeriodEnum.HY2) {
			map.put(PeriodEnum.HY1, 0);
			map.put(PeriodEnum.Q2, 0);
			return map;
		}
		// dernier cas possible Q4
		map.put(PeriodEnum.Q3, 0);
		return map;
	}

	public int getDivideFactor() {
		return divideFactor;
	}

	public void setDivideFactor(int divideFactor) {
		this.divideFactor = divideFactor;
	}

	public int getNbDays() {
		return nbDays;
	}

	public void setNbDays(int nbDays) {
		this.nbDays = nbDays;
	}

	/**
	 * @return the keyDescription
	 */
	public String getKeyDescription() {
		return keyDescription;
	}

	/**
	 * @param keyDescription the keyDescription to set
	 */
	public void setKeyDescription(String keyDescription) {
		this.keyDescription = keyDescription;
	}

}
