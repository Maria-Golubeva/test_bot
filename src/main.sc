require: slotfilling/slotFilling.sc
  module = sys.zb-common
require: phone.sc
require: functions.js
require: wheather.sc
require: discount.sc
require: city.sc
require: dicts/discount.yaml
    var = discountInfo
    
require: city/cities-ru.csv
    module = sys.zb-common
    name = Cities
    var = Cities
  


init:
    $global.$converters = {};

    $global.$converters.CityConverter = function CityConverter(parseTree) {
    var id = parseTree.Cities[0].value; 
    return Cities[id].value;
    };

    bind("postProcess", function($context){
        $context.session.lastState = $context.currentState;
        log("@@@@@" + toPrettyString($context.session));
    });

theme: /
    
    state: Welcome
        q!: *start
        q!: (привет*/здравствуй*/hello)
        random:
            a: Добрый день! 
            a: Здравствуйте!
        a: Меня зовут {{ capitalize($injector.botName) }}. Чем я могу помочь?
        script: 
            $response.replies = $response.replies || [];
            $response.replies.push( {
                type: "image",
                imageUrl: "https://fun-cats.ru/wp-content/uploads/c/a/b/cab5fbb958aa5a8bcfc539104443ad3f.jpeg",
                text: "Это я!"
            });
        
        state: Wheather
            q: * погод* *
            q: Отмена || fromState = /Phone/Ask, onlyThisState = true
            a: Я могу подсказать Вам прогноз погоды
            if: $client.phone
                go!: /Phone/Confirm
            else:
                go!: /Phone/Ask
        
        state: Time
            q: * врем* *
            a: Я могу подсказать текущее время
            
    state: HowAreYou
        q!: как дела?
        a: Чудесно, спасибо, что спросили

    state: Bye
        intent!: /пока
        a: Пока пока

    state: NoMatch
        event!: noMatch
        a: Простите, я не поняла. Пожалуйста, переформулируйте Ваш вопрос
        go!: {{ $session.lastState }}

    state: Match
        event!: match
        a: {{$context.intent.answer}}