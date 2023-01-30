theme: /Discount
    
    state: OfferDiscount
        script:
            var nowDayOfWeek = $jsapi.dateForZone("Europe/Moscow", "EEEE");
            var discount = discountInfo[nowDayOfWeek];
            if (discount) {
                var nowDate = $jsapi.dateForZone("Europe/Moscow", "dd.MM.YYYY");
                var answerText = "Хочу отметить, что вам крупно повезло! Сегодня (" + nowDate + ") действует акция!";
                $reactions.answer(answerText);
                $reactions.answer(discount);
            }
        go!: /City/Departure