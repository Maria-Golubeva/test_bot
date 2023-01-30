patterns:
    $phone = $regexp<79\d{9}>
    $city = $entity<Cities> || converter = $converters.CityConverter

theme: /Phone
    
    state: Ask || modal = true
        a: Для продолжения напишите, пожалуйста, ваш номер в формате 79000000000
        buttons:
            "Отмена"
        
        state: Get
            q: $phone
            go!: /Phone/Confirm
            
        state: Wrong
            q: *
            a: Что-то не похоже на номер телефона...
            go!: /Phone/Ask
            
    state: Confirm
        script:
            $temp.phone = $parseTree._phone || $client.phone;
        a: Ваш номер {{ $temp.phone }}, верно?
        script:
            $session.probablyPhone = $temp.phone
        
        buttons:
            "Да"
            "Нет"
            
        state: Yes
            q: (да/верно/точно)
            script:
                $client.phone = $session.probablyPhone;
                delete $session.probablyPhone;
            a: Хорошо, я поняла
            go!: /Discount/OfferDiscount
            
        state: No
            q: (нет/не [верно])
            go!: /Phone/Ask
