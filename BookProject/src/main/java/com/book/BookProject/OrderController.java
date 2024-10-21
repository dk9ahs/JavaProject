//package com.book.BookProject;
//
//import com.book.BookProject.order.Order;
//import com.book.BookProject.order.OrderService;
//import com.book.BookProject.user.UserEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpStatus;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/order")
//public class OrderController {
//
//    private final OrderService orderService;
//    private final LoginService loginService;
//
//    public OrderController(OrderService orderService, LoginService loginService) {
//        this.orderService = orderService;
//        this.loginService = loginService;
//    }
//
//    // 주문 생성 페이지로 이동 (로그인된 회원 정보를 전달)
//    @GetMapping("/create")
//    public String showCreateOrderPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
//        String userId = userDetails.getUsername();
//        UserEntity user = loginService.getUserById(userId);
//
//        model.addAttribute("user", user);
//        model.addAttribute("order", new Order());  // 빈 주문 객체도 추가
//
//        return "/member/order/orderCreate";  // 주문 생성 페이지로 이동
//    }
//
//    // 주문 저장
//    @PostMapping("/saveOrder")
//    public ResponseEntity<Map<String, Object>> saveOrder(@RequestBody Map<String, String> orderData, @AuthenticationPrincipal UserDetails userDetails) {
//
//        System.out.println("saveOrder 메서드 호출됨");  // 로그 추가
//
//        try {
//            // 로그인된 사용자 정보를 가져옴
//            String userId = userDetails.getUsername();
//            UserEntity user = loginService.getUserById(userId);
//
//            // 주문 정보 생성
//            Order order = Order.builder()
//                    .member(user)  // 주문자 정보 설정
//                    .merchantUid(System.currentTimeMillis())  // 고유한 주문 번호 생성
//                    .orderDate(LocalDateTime.now())
//                    .status("ORDERED")  // 기본 주문 상태 설정
//                    .totalAmount(Double.parseDouble(orderData.get("amount")))  // 결제 금액 설정
//                    .shippingAddress(orderData.get("buyerAddr"))  // 배송 주소 설정
//                    .detailAddress(orderData.get("buyerDetailAddr"))  // 배송 주소 설정
//                    .recipientName(orderData.get("buyerName"))  // 수령인 이름 설정
//                    .recipientPhone(orderData.get("buyerTel"))  // 수령인 연락처 설정
//                    .paymentMethod("CARD")  // 결제 방법 설정
//                    .paymentStatus("PAID")  // 결제 상태 설정
//                    .build();
//
//            // 주문 정보 저장
//            Order savedOrder = orderService.createOrder(order);
//
//            // 응답 데이터 생성
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("order", savedOrder);
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "주문 저장 중 오류 발생"));
//        }
//    }
//
//    // 아임포트 결제 검증 로직 (예시)
//    private boolean verifyPaymentWithIamport(String impUid, String merchantUid) {
//        // 실제 서버에서는 아임포트 API를 호출하여 결제를 검증하는 로직이 들어가야 합니다.
//        // 여기서는 예시로, 무조건 결제가 유효하다고 가정합니다.
//        return true;  // 결제가 성공적이었다고 가정
//    }
//
//    // 특정 주문 조회
//    @GetMapping("/{orderId}")
//    public String getOrder(@PathVariable Long orderId, Model model) {
//        Order order = orderService.getOrderById(orderId);
//        if (order != null) {
//            model.addAttribute("order", order);
//            return "member/orderDetail";
//        } else {
//            return "error";
//        }
//    }
//
//    // 모든 주문 조회
//    @GetMapping
//    public String getAllOrders(Model model) {
//        model.addAttribute("orders", orderService.getAllOrders());
//        return "/order/orderList";
//    }
//
//    // 주문 삭제
//    @PostMapping("/{orderId}/delete")
//    public String deleteOrder(@PathVariable Long orderId) {
//        orderService.deleteOrder(orderId);
//        return "redirect:/order";
//    }
//}