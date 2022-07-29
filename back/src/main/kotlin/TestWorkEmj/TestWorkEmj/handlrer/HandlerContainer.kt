package TestWorkEmj.TestWorkEmj.handlrer

class HandlerContainer() {
    private val handlerMap: MutableMap<String, Handler> = hashMapOf()

    init {
        handlerMap["generation"] = GenerationHandler()
        handlerMap["auto"] = AutoHandler()
    }

    fun retrieveHandler(handlerIdentifier: String?): Handler? {
        return handlerMap[handlerIdentifier]
    }
}