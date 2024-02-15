const { join } = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const webpack = require('webpack')
const { merge } = require('webpack-merge')
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
//const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin

const devConfig = require('./webpack.dev.config')
const prodConfig = require('./webpack.prod.config')

const TARGET = process.env.npm_lifecycle_event
module.exports = merge(
  {
    //entry: ['@babel/polyfill', 'url-search-params-polyfill', './src/index.js'],
    entry: {
      appMain: ['@babel/polyfill', 'url-search-params-polyfill', './src/index.js']
    },
    output: {
      path: `${__dirname}/build`,
      //filename: 'appMain.js',
      filename: '[name].js',
      publicPath: '/',
      chunkFilename: 'appMain-c' + '[id]-[chunkhash].js'
    },
    module: {
      rules: [
        {
          enforce: 'pre',
          test: /\.js$/,
          exclude: /node_modules/,
          loader: 'eslint-loader',
          options: {
            emitError: true,
            failOnError: true
          }
        },
        {
          test: /\.(js|jsx|mjs)$/,
          exclude: /node_modules/,
          loader: 'babel-loader',
          query: {
            presets: [
              [
                '@babel/preset-env',
                {
                  useBuiltIns: 'entry',
                  targets: '>0.2%, not dead, ie > 10, not op_mini all'
                }
              ],
              '@babel/preset-react',
              {
                plugins: [['@babel/plugin-proposal-class-properties'], ['@babel/plugin-transform-object-assign']]
              }
            ]
          }
        },
        {
          test: /\.(png|jpg|gif|woff|woff2|eot|ttf|svg)$/,
          use: [
            {
              loader: 'url-loader',
              options: {}
            }
          ]
        },
        {
          test: /\.(scss|sass|css)$/,
          use: ['style-loader', 'css-loader', 'sass-loader']
        }
      ]
    },
    plugins: [
      new MiniCssExtractPlugin({
        filename: '[name].css'
      }),
      new HtmlWebpackPlugin({
        template: join(__dirname, 'public', 'index.html'),
        inject: true,
        hash: true
      }),
      new webpack.ProvidePlugin({
        React: 'react'
      })
      // new BundleAnalyzerPlugin({
      //   withReport: true
      // })
    ]
  },
  TARGET !== 'build' ? { ...devConfig } : { ...prodConfig }
)
