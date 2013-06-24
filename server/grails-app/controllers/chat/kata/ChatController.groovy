package chat.kata

class ChatController {
	ChatService chatService

	def list(Integer seq) {
		if(hasErrors()){
			log.error("Invalid seq: ${errors.getFieldError('seq').rejectedValue}")
			//TODO: implement me
		}
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


	def send(){
		ChatMessage msgs = new ChatMessage(request.JSON)
		chatService.putChatMessage(msgs);
		render(status: 201)
	}
}
