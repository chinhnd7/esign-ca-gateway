package vn.intrustca.esigncagateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import vn.intrustca.esigncagateway.payload.ChainData;
import vn.intrustca.esigncagateway.payload.RaUserCertificate;
import vn.intrustca.esigncagateway.payload.UserCertificate;
import vn.intrustca.esigncagateway.payload.exception.*;
import vn.intrustca.esigncagateway.payload.request.RaGetCertRequest;
import vn.intrustca.esigncagateway.payload.request.GetCertRequest;
import vn.intrustca.esigncagateway.payload.request.RaLoginRequest;
import vn.intrustca.esigncagateway.payload.response.GetCertResponse;
import vn.intrustca.esigncagateway.payload.response.RaLoginResponse;
import vn.intrustca.esigncagateway.utils.RestHelper;
import vn.intrustca.esigncagateway.utils.ServiceUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class GatewayService {
    private static final Logger logger = LoggerFactory.getLogger(GatewayService.class);

    @Value("${ra.endpoint}")
    private String endpoint;

    @Value("${ra.username}")
    private String userName;

    @Value("${ra.password}")
    private String password;

    public GetCertResponse getCerts(GetCertRequest request, HttpServletRequest httpRequest, BindingResult bindingResult) throws JsonProcessingException, ServiceException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        GetCertResponse response = new GetCertResponse();
        try {
            RaLoginRequest loginRequest = new RaLoginRequest(userName, password);
            RestHelper restHelper = new RestHelper();
            restHelper.init(endpoint);

            RaLoginResponse loginResponse = restHelper.callService("auth/signin", loginRequest, null, httpRequest, RaLoginResponse.class);
            if(loginResponse.getCode() == 0){
                RaGetCertRequest raGetCertRequest = new RaGetCertRequest(request.getUserId());
                List<RaUserCertificate> responseCerts = restHelper.getCerts("getcertuser", raGetCertRequest, loginResponse.getAccessToken(), httpRequest);
                response.setUserCertificates(ServiceUtils.adaptRaResponseToNeac(responseCerts));
                response.setTransactionId(request.getTransactionId());
            }else {
                throw new BusinessException(ExceptionCode.LOGIN_FAIL);
            }
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.INVALID_CERT);
        }
        return response;
    }

    private List<UserCertificate> adaptRaResponseToNeac (List<RaUserCertificate> responseCerts) {
        List<UserCertificate> listUserCerts = new ArrayList<>();
        for (RaUserCertificate cert : responseCerts) {
            UserCertificate userCertificate = new UserCertificate();
            userCertificate.setCert_id(cert.getCertificateId());
            userCertificate.setCert_data(cert.getBase64Certificate());
            userCertificate.setChain_data(new ChainData());
            userCertificate.setSerial_number(cert.getCertificateSerialNumber());
            listUserCerts.add(userCertificate);
        }
        return listUserCerts;
    }
}
