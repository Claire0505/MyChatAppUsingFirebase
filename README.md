# MyChatAppUsingFirebase
使用Firebase來做簡單的聊天通訊<br/>
參考資料來源：(文章裡所支援的版本和現在使用的不同，所以有些寫法有變動)<br/>
https://code.tutsplus.com/zh-hant/tutorials/how-to-create-an-android-chat-app-using-firebase--cms-27397 <br/>
<hr/>
隱藏鍵盤(onCreate()) <br/>
this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); <br/>
點擊按鈕後隱藏鍵盤 <br/>
InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); <br/>
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
<br/>
<hr/>
build.gradle <br/>
apply plugin: 'com.google.gms.google-services' <br/>
// Firebase Authentication <br/>
implementation 'com.google.firebase:firebase-core:16.0.5' <br/>
implementation 'com.google.firebase:firebase-auth:16.0.5' <br/>
<br/>
// Firebase UI <br/>
// Used in FirebaseUIActivity. <br/>
implementation 'com.firebaseui:firebase-ui-auth:4.2.1' <br/>
<br/>
// FirebaseUI for Firebase Realtime Database<br/>
implementation 'com.firebaseui:firebase-ui-database:4.2.1' <br/>
<br/>
// Firebase Database <br/>
implementation 'com.google.firebase:firebase-database:16.0.4' <br/>
<br/>

  //顯示聊天訊息(這邊讀取資料的方式和範例上不同)<br/>
    private void displayChatMessage() { <br/>
        ListView listOfMessage = findViewById(R.id.list_of_message);

        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(FirebaseDatabase.getInstance().getReference(), ChatMessage.class)
                .setLayout(R.layout.message)
                .build();

         adapter = new FirebaseListAdapter<ChatMessage>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));

            }
        };

        listOfMessage.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
