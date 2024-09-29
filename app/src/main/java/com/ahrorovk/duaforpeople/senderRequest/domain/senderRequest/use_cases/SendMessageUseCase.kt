package com.ahrorovk.duaforpeople.senderRequest.domain.senderRequest.use_cases

import com.ahrorovk.duaforpeople.senderRequest.domain.SenderRequestRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: SenderRequestRepository
) {
}