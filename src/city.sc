theme: /City
    
    state: Direction
        intent!: /direction
        script:
            $session.departureCity = capitalize($nlp.inflect($parseTree._departure, "nomn")) || $parseTree._departure;
            $session.arrivalCity = $nlp.inflect($parseTree._destination, "nomn") || $parseTree._destination;
            $session.date = $parseTree._date.day + "/" + $parseTree._date.month + "/" + $parseTree._date.year;
        a: {{ $session.date }} отправляемся из города {{ $session.departureCity }} в город {{ $session.arrivalCity }}.
    
    state: Departure
        a: Назовите, пожалуйста, город отправления
        
        state: Get
            q: * $city *
            script:
                log('@@@@ \n parseTree.city: \n' + toPrettyString($parseTree.city) + '\n');
                # var id = $parseTree.city[0].value;
                # $session.departureCity = Cities[id].value;
                # $session.departureCity = $parseTree.city[0].value;
                $session.departureCity = $parseTree._city;
            a: Итак, город отправления: {{ $session.departureCity.name }}
            go!: /City/Arrival
            
        state: localCatchAll
            q: * || fromState = /City/Departure
            a: Простите, я вас не поняла
            go!: {{ $session.lastState }}
    
    state: Arrival
        a: Назовите, пожалуйста, город прибытия
        
        state: Get
            q: * $city *
            script:
                $session.arrivalCity = $parseTree._city;
            a: Итак, город прибытия: {{ $session.arrivalCity.name }}
            go!: /Wheather/CurrentWheather
