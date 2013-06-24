package chat.kata

class ChatController {
	ChatService chatService
	static constraints = { seq instanceof:Integer }

	def list(Integer seq) {
		if(hasErrors()){
			log.error("Invalid seq: ${errors.getFieldError('seq').rejectedValue}")
			render(status:400, contentType: "text/json"){  error="Invalid seq parameter"  }
		}
		else{
			final Collection<ChatMessage> collector = []
			final int lastSequence = chatService.collectChatMessages(collector, seq)

			render(contentType: "text/json"){
				messages = []
				for (int i = 0; i < collector.size(); i++) {
					ChatMessage chat = collector[i]
					messages.add(nick:chat.nick, message:chat.message)
				}
				last_seq = lastSequence
			}
		}
	}


	def send(){
		def msg = new ChatMessage(request.JSON)

		if(request.JSON){
			if(msg.validate()){
				chatService.putChatMessage(msg);
				render(status: 201)
			}
			else{
				if(msg.errors.hasFieldErrors("nick"))
					render(status:400, contentType:"text/json"){ error="Missing nick parameter"}
				else{
					if(msg.errors.hasFieldErrors("message"))
						render(status:400, contentType:"text/json"){ error="Missing message parameter"}
				}
			}
		}
		else{
			render(status:400, contentType:"text/json"){ error="Invalid body" }
		}
	}
}
