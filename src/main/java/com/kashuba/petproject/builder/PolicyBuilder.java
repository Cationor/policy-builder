package com.kashuba.petproject.builder;

import com.kashuba.petproject.model.entity.Client;
import com.kashuba.petproject.model.entity.Policy;
import com.kashuba.petproject.util.DateConverter;
import com.kashuba.petproject.util.ParameterKey;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.eclipse.jdt.internal.compiler.env.ISourceType;

import java.time.LocalDate;
import java.util.Map;

public class PolicyBuilder  {
    private PolicyBuilder(){
    }

    public static Policy buildPolicy(Map<String, String> clientParameters) {
        Policy buildingPolicy = new Policy();
        System.out.println(clientParameters.get(ParameterKey.TERM_OF_VALIDITY));
        System.out.println(clientParameters.get(ParameterKey.FIRST_NAME));
        LocalDate termOfValidity = LocalDate.parse(clientParameters.get(ParameterKey.TERM_OF_VALIDITY));
        buildingPolicy.setTermOfValidity(DateConverter.convertToLong(termOfValidity));
        buildingPolicy.setRegisteredObject((String) clientParameters.get(ParameterKey.REGISTERED_OBJECT));
        buildingPolicy.setSumInsured(Integer.parseInt(clientParameters.get(ParameterKey.SUM_INSURED)));
        buildingPolicy.setContractCurrency((String) clientParameters.get(ParameterKey.CONTRACT_CURRENCY));
        buildingPolicy.setFirstName((String) clientParameters.get(ParameterKey.FIRST_NAME));
        buildingPolicy.setSecondName((String) clientParameters.get(ParameterKey.SECOND_NAME));
        buildingPolicy.setInsuranceCoverageArea((String) clientParameters.get(ParameterKey.INSURANCE_COVERAGE_AREA));
        buildingPolicy.setInsuranceType((String) clientParameters.get(ParameterKey.INSURANCE_TYPE));
        return buildingPolicy;
    }


}
