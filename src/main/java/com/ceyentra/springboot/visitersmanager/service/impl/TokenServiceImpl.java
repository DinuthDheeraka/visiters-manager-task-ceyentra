/**
 * @author :  Dinuth Dheeraka
 * Created : 7/14/2023 12:04 AM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.repository.TokenRepository;
import com.ceyentra.springboot.visitersmanager.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public void deleteTokenByUserId(int id) {
        tokenRepository.deleteByUserId(id);
    }
}
