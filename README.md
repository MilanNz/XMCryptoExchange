# :octocat: XMCryptoExchange
XMCryptoExchange is a library for websocket communication with [Binance](www.binance.com) cryptocurrency exchange market.
It is build on top of the [Retrofit - Square lib](http://square.github.io/retrofit/).

[Binance API](https://www.binance.com/restapipub.html#wss-endpoint)

# How to use it?

# Stream API:
###### Trade websocket endpoint
```
// Subscribe on Binance trade stream.
ExchangeBinanceStream binanceTradeStream = new ExchangeBinanceStream()
      .setPair(new Pair<>(Currency.IOTA, Currency.ETH));

binanceTradeStream.stream(new ExchangeWebSocketListener.StreamListener<Trade>() {
    @Override
    public void onMessage(Trade trade) {
        logger.info("new msg: " + trade.toString());
        logger.info("price: " + trade.getPrice());
        logger.info("quantity: " + trade.getQuantity());
    }

    @Override
    public void onClosed(int code, String reason) {
        logger.error("connection closed: " + reason + ", code: " + code);
    }

    @Override
    public void onConnected(Response response) {
        logger.info("connected: " + response.message() + ", code: " + response.code());
    }

    @Override
    public void onFailure(Throwable throwable, Response response) {
        logger.error("connection failure: " + throwable.getMessage());
    }
});

// To close connection
binanceStream.closeStream();
```

##### Depth websocket endpoint:

```
ExchangeBinanceStream binanceStream = new ExchangeBinanceStream()
          .setPair(new Pair<>(Currency.IOTA, Currency.ETH));
binanceStream.depthStream(new ExchangeWebSocketListener.StreamListener<JsonObject>()
  @Override
    public void onMessage(JsonObject json) {
        logger.info("depth json: " + json);
    }

    @Override
    public void onClosed(int code, String reason) {
    }

    @Override
    public void onConnected(Response response) {
    }

    @Override
    public void onFailure(Throwable throwable, Response response) {
    }
});

```

###### User data websocket endpoint:
```
ExchangeBinanceStream userDataStream = new ExchangeBinanceStream()
      .setListenKey("xxxxxxx");
userDataStream.userDataStream(new ExchangeWebSocketListener.StreamListener<JsonObject>()...);
```
###### Kline websocket endpoint:
```
ExchangeBinanceStream klineStream = new ExchangeBinanceStream()
      .setPair(new Pair<>(Currency.IOTA, Currency.ETH))
      .setInterval("1m");
klineStream.klineStream(...);
```

# HTTPS API:

Supported routes:
- POST test order
- GET all prices
- GET prices in 24h
- POST new order (Requires API-key and Secret)
- GET order status (Requires API-key and Secret)
- DELETE cancel order (Requires API-key and Secret)
- GET current open orders (Requires API-key and Secret)
- GET all orders (Requires API-key and Secret)
- GET account information (Requires API-key and Secret)
- GET account trade list (Requires API-key and Secret)

#### Get all prices example (Does not require API-key and Secret):

```
ExchangeApiBinance binance = ExchangeApiBinanceImpl.newInstance();
binance.getAllPrices(response -> {
    if (response.isSuccessful()) {
        logger.info(response.body());
    }
}, fail-> {
    fail.printStackTrace();
});
```

#### Create new order (Requires API-key and Secret)
```
ExchangeApiBinance apiBinance = ExchangeApiBinanceImpl.newInstance("api-key", "secret");

Order order = new Order();
order.setSymbol(Currency.LTC, Currency.ETH); // Symbol pair
order.setSide(OrderSide.BUY); // side is buy
order.setType(OrderType.LIMIT); // type limit
order.setTimeInForce(TimeInForce.GTC); // time in force GTC
order.setQuantity("1"); // quantity for this example is 1
order.setPrice("0.1"); // set price
order.setTimestamp(System.currentTimeMillis()); // set timestemp

apiBinance.setTestOrder(order, response -> {
    if (response.isSuccessful()) {
        logger.info(response.body());
    } else {
        logger.info(response.message());
    }
}, failed -> {
    logger.info(failed.getMessage());
});
```

# Libs
- [Retrofit - Square lib](http://square.github.io/retrofit/)
- [Google Gson](https://github.com/google/gson)

# Contribution
Let me know if you want to help/improve or if you have any troubles/questions, feel free to ask ;)

# Will be supported soon:
- Maven/Gradle
- More documentation

### I'm also trader, in case you want to buy me a :beer: :P
Bitcoin: 1JVQnkuKTWA6i4AMXFsksdJgt4B3o1yfgA <br>
Ethereum: 0x11a9df6086638aa3fe3e1023d7190f5f764cf7db <br>
TRX: 0x11a9df6086638aa3fe3e1023d7190f5f764cf7db
