package com.likelion.lionlib.service;

import com.likelion.lionlib.domain.Book;
import com.likelion.lionlib.domain.Member;
import com.likelion.lionlib.domain.Reservation;
import com.likelion.lionlib.dto.CustomUserDetails;
import com.likelion.lionlib.dto.request.ReservationRequest;
import com.likelion.lionlib.dto.response.ReservationCountResponse;
import com.likelion.lionlib.dto.response.ReservationResponse;
import com.likelion.lionlib.exception.ReservationNotFoundException;
import com.likelion.lionlib.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository repository;
    private final GlobalService globalService;

    // 도서 예약
    public ReservationResponse addReservation(ReservationRequest request, CustomUserDetails customUserDetails) {
        Member member = globalService.findMemberById(customUserDetails.getId());
        Book book = globalService.findBookById(request.getBookId());

        Reservation savedReservation = Reservation.builder()
                .member(member)
                .book(book)
                .build();
        repository.save(savedReservation);
        return ReservationResponse.fromEntity(savedReservation);
    }

    // 예약 조회
    public ReservationResponse getReservation(Long reservationId) {
        Reservation reservation = repository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        return ReservationResponse.fromEntity(reservation);
    }

    // 예약 삭제
    public void deleteReservation(Long reservationId) {
        Reservation reservation = repository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        repository.deleteById(reservationId);
    }

    // 멤버의 예약 리스트 조회
    public List<ReservationResponse> getReservationsByMember(Long memberId) {
        Member member = globalService.findMemberById(memberId);
        List<Reservation> reservations = repository.findAllByMember(member);

        return reservations.stream()
                .map(ReservationResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 도서 예약 수 조회
    public ReservationCountResponse getReservationCount(Long bookId) {
        Book book = globalService.findBookById(bookId);

        return ReservationCountResponse.fromEntity(repository.countByBook(book));
    }
}
