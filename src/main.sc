patterns:
    $deleteContent = (убрал*/удалил*/удаляют*/удаляет*)
    $disappearPast = (пропал*/попал*/исчез*/делся/делись*/подевал*/отсутств*)
    $earlier = (ранее/раньше)
    $loadPres = (открывает*/показывает*/показывают/доступен/доступн*/работает/загружает*/грузит*
        |возможн*/отображает*/воспроизводит*/вас производит*/включает*/находит*)
    
    $mediaContent = (фильм*/филиал/сим*/~фин/$series/мульт*/~серия/~сезон/эпизод*/~контент/~кино
        |ролик*/трейлер*/видео*/видос*/~серия)
    
    $series = (сериал*/терял/серчик*/сирич)
    $unavailable = (не $loadPres/недоступен/недоступн*/невозможн*)

require: slotfilling/slotFilling.sc
  module = sys.zb-common
require: number/number.sc
  module = sys.zb-common
require: patterns.sc
theme: /

    state: Start
        q!: $regex</start>
        a: Начнём.

    # state: Hello
    #     intent!: /привет
    #     a: Привет привет
        
    # state: HowAreYou
    #     q!: как дела?
    #     a: Замечательно! А у вас как дела?
        
    
    state: DeletedContent
        q!: пропал фильм *
        q!: {($disappearPast/$unavailable) * [$oneWord] [$oneWord]}
        q!: {(нет/нету) * $mediaContent}
        q!: * $where * $mediaContent *
        q!: * {[$where/куда] * $disappearPast * $mediaContent} *
        q!: * {$unavailable * $mediaContent} *
        q!: * [$why] * {(не ((могу/смог*/могл*/можем) * (найти/*смотреть/просмотр*)/нашел*/вижу)) * ($mediaContent/в каталог*)} *
        q!: * {$earlier * показывал* * $mediaContent} *
        q!: * {удален* * (наличи*/доступ*) * $mediaContent} *
        q!: * {(наличи*/доступ*) * $mediaContent} *
        q!: * {($why/(из/по) $subscription/причин*/(с/из/в) (ivi/иви)) * ($disappearPast/удалени*/нет/нету) * $mediaContent} *
        q!: * {($why/(из/по) $subscription/причин*) * ($unavailable [$oneWord] [к/для] просмотр*) * $mediaContent} *
        q!: * {($why/(из/по) $subscription/причин*) * (просмотр* $unavailable/$unavailable [$oneWord] [к/для] просмотр*) * ($mediaContent/сейчас)} *
        q!: * {(проблем*/не находит*/нет/нету) * ([в] поиск*/с) * $mediaContent} *
        q!: * {$mediaContent * (теперь/больше/*смотреть/сейчас) * (нет/нету)} *
        q!: * {(прервал*/прерван*/продолжить/станет доступен) * просмотр* * $mediaContent} *
        q!: * {код ошибки 4521} * $weight<+1.0>
        q!: * чтобы * {(вернули) * $mediaContent} *
        q!: * {(был* [$oneWord] (удален*/убран*)) * $mediaContent} *
        q!: * $when * (появится/вернет*/(можно будет/смогу/сможем) *смотреть) * $mediaContent *
        q!: * {(не досмотрел*/недосмотрел*) * $mediaContent} *
        q!: * {*смотрел* * $mediaContent} * {(сейчас/теперь/раньш/сегодня) * (не (могу/получается/найти))} *
        q!: * {(верни*) * $mediaContent} *
        q!: * [$mediaContent] * $where * ((~новый/$Number) (сезон*/~серия)) * [$mediaContent] *
        q!: * $why [то] (нет/нету/($ne (могу/можем/получается/выходит) (найти/отыскать))/($ne (вижу/видим))) * ([$mediaContent] *)
        
        a: запрос про удаленный контент

    # state: Bye
    #     intent!: /пока
    #     a: Пока пока

    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}

    # state: Match
    #     event!: match
    #     a: {{$context.intent.answer}}