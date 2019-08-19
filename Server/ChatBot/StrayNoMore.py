from flask import Flask, render_template, request
from chatterbot import ChatBot
from chatterbot.trainers import ChatterBotCorpusTrainer
from chatterbot.trainers import ListTrainer
from dotenv import load_dotenv
load_dotenv('.env')

app = Flask(__name__)

bot = ChatBot("Candice")
trainer = ChatterBotCorpusTrainer(bot)
trainer.train("chatterbot.corpus.english")

@app.route("/")
def home():    
    return render_template("home.html") 
@app.route("/get")
def get_bot_response():    
    userText = request.args.get('msg')    
    return str(bot.get_response(userText)) 

def main():
    app.debug = True
    app.run(host='0.0.0.0')

if __name__ == "__main__":    
    main()
    