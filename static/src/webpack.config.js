/* eslint-disable no-var */
var path = require('path');
var webpack = require('webpack');


module.exports = {
  entry: [
    'webpack-hot-middleware/client',
    './js/index'
  ],
  devtool: 'eval-source-map',
  output: {
    path: __dirname,
    filename: 'bundle.js',
    publicPath: '/'
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    new webpack.NoErrorsPlugin()
  ],
  module: {
    loaders: [
      {
        test: /\.js$/,
        loaders: ['babel'],
        include: path.join(__dirname, 'js')
      },
      {
        test: /\.css$/,
        loader: "style-loader!css-loader",
        include: path.join(__dirname, 'css')
      },
      {
        test: /\.png$/,
        loader: "url-loader?limit=100000"
      },
      {
        test: /\.jpg$/,
        loader: "file-loader"
      },
      {
        test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
        loader: "url-loader?limit=10000&mimetype=application/font-woff"
      },
      {
        test: /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
        loader: "file-loader"
      },
      {
        test: require.resolve("jquery"),
        loader: "expose?$!expose?jQuery"
      }
    ]
  }
};
