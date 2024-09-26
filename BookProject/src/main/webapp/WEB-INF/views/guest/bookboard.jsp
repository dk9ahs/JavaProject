<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book List</title>
    <style>
        .book-list-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        .book-list-table th, .book-list-table td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        .book-list-table th {
            background-color: #f4f4f4;
            text-align: center;
        }
        .book-list-table td {
            vertical-align: top;
        }
        .book-image {
            width: 150px;
            height: auto;
            margin-right: 10px;
        }
        .price {
            color: red;
            font-weight: bold;
        }
        .book-info {
            display: flex;
            gap: 10px;
        }
        .book-details {
            display: flex;
            flex-direction: column;
        }
        .book-actions {
            display: flex; /* Set the buttons to display in a row */
            gap: 5px; /* Space between the buttons */
            justify-content: center; /* Center the buttons horizontally */
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
        .remarks {
            width: 100px; 
            text-align: center;
        }
    </style>
</head>
<body>
    <table class="book-list-table">
        <thead>
            <tr>
                <th>번호</th>
                <th>도서 정보</th>
                <th>가격</th>
                <th class="remarks">비고</th>
            </tr>
        </thead>
        <tbody>
            <!-- Repeat this block for each book -->
            <tr>
                <td>1</td>
                <td>
                    <div class="book-info">
                        <img src="https://image.aladin.co.kr/product/34474/76/cover500/k292932634_3.jpg" alt="Book Image" class="book-image">
                        <div class="book-details">
                            <p><strong>이중 하나는 거짓말</strong></p>
                            <p>김예현 | 문학동네 | 2024년 8월</p>
                            <p>판매지수: 158,630</p>
                        </div>
                    </div>
                </td>
                <td class="price">14,400원 (10% 할인)</td>
                <td class="remarks">
                    <div class="book-actions">
                        <button class="button">장바구니</button>
                        <button class="button">바로구매</button>
                        <button class="button">보관함</button>
                    </div>
                </td>
            </tr>
            <tr>
                <td>2</td>
                <td>
                    <div class="book-info">
                        <img src="https://image.aladin.co.kr/product/34634/14/cover500/8959897221_1.jpg" alt="Book Image" class="book-image">
                        <div class="book-details">
                            <p><strong>트렌드 코리아 2025</strong></p>
                            <p>김난도 외 | 미래의창 | 2024년 9월</p>
                            <p>판매지수: 75,740</p>
                        </div>
                    </div>
                </td>
                <td class="price">18,000원 (10% 할인)</td>
                <td class="remarks">
                    <div class="book-actions">
                        <button class="button">예약 장바구니</button>
                        <button class="button">예약 바로구매</button>
                        <button class="button">보관함</button>
                    </div>
                </td>
            </tr>
            <!-- Repeat block ends -->
        </tbody>
    </table>
</body>
</html>