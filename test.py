from pymongo import MongoClient

client = MongoClient("mongodb://localhost:27017/")
db = client["lol"]  # replace with your database name

# Get the collection
collection = db["lol_lol"]  # replace with your collection name

# Get one document from the collection
doc = collection.find_one()

if doc is not None:
    print(doc)
else:
    print("No documents found in the collection")

# Wait for user to type 'quit' to exit
while True:
    input_str = input("Type 'quit' to exit\n")
    if input_str.lower() == 'quit':
        break