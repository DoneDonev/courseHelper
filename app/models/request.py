from pydantic import BaseModel, Field
from typing import Optional
from app.models.enums import ModelProvider, UriProvider
from app.models.video import Video
from app.models.conversation import ConversationHistory

class AIRequest(BaseModel):
    """Schema for an AI request"""
    model_provider: ModelProvider  # Which AI provider (Gemini, OpenAI, etc.)
    model_name: str = Field(description="The name of a model", example="gemini-1.5-flash")
    system_instruction_text: Optional[str] = Field(
        default=None, example="You are a helpful AI that explains educational content."
    )
    conversation_history: Optional[ConversationHistory] = None  # Optional past messages
    api_key: Optional[str] = Field(default=None, example="1234567890")  # API key for authentication
<<<<<<< HEAD
    collection_name: Optional[str] = Field(
        default=None, example="my_collection"
    )  # Name of the collection in the vector database
=======
>>>>>>> 143d6af (Initial Python service commit)

class UriRequest(BaseModel):
    video: Video = None
    uri_provider: UriProvider = None
    api_key:str = Field(default= None, description="API key if needed with an URI provider (needed with Gemini).", example="123456")
