<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book List</title>
    <style>
        .book-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
            padding: 20px;
        }
        .book-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
        }
        .book-image {
            width: 130px;
            height: auto;
            margin-bottom: 10px;
        }
        .book-title {
            font-weight: bold;
            margin: 5px 0;
        }
        .book-author {
            color: #555;
            font-size: 14px;
        }
        .book-price {
            color: red;
            font-weight: bold;
            margin: 5px 0;
        }
        .sales-point {
            font-size: 12px;
            color: #888;
        }
        .book-actions {
            display: flex;
            gap: 5px;
            justify-content: center;
            margin-top: 10px;
        }
        .button {
            background-color: #ff6f61;
            color: white;
            padding: 5px 8px;
            border: none;
            cursor: pointer;
            border-radius: 3px;
            font-size: 12px;
        }
        .button:hover {
            background-color: #ff4c3b;
        }
    </style>
</head>
<body>
    <div class="book-grid">
        <!-- Repeat this block for each book -->
        <div class="book-card">
            <img src="https://image.aladin.co.kr/product/34474/76/cover500/k292932634_3.jpg" alt="Book Image" class="book-image">
            <p class="book-title">이중 하나는 거짓말</p>
            <p class="book-author">김예현 | 문학동네 | 2024년 8월</p>
            <p class="book-price">14,400원 (10% 할인)</p>
            <p class="sales-point">판매지수: 158,630</p>
            <div class="book-actions">
                <button class="button">장바구니</button>
                <button class="button">바로구매</button>
                <button class="button">보관함</button>
            </div>
        </div>
        <div class="book-card">
            <img src="https://image.aladin.co.kr/product/34634/14/cover500/8959897221_1.jpg" alt="Book Image" class="book-image">
            <p class="book-title">트렌드 코리아 2025</p>
            <p class="book-author">김난도 외 | 미래의창 | 2024년 9월</p>
            <p class="book-price">18,000원 (10% 할인)</p>
            <p class="sales-point">판매지수: 75,740</p>
            <div class="book-actions">
                <button class="button">예약 장바구니</button>
                <button class="button">예약 바로구매</button>
                <button class="button">보관함</button>
            </div>
        </div>
        <!-- Repeat block ends -->
    </div>
</body>
</html>