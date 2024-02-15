module.exports = {
  devtool: 'source-map',
  devServer: {
    overlay: true,
    historyApiFallback: {
      disableDotRule: true
    },
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
      Accept: 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8'
    },
    inline: true,
    port: 9020
  }
}
