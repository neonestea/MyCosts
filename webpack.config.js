const path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    mode: 'development',
    devtool: 'source-map',
    entry: {
        'mainPage': path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'main.js'),
        /*'categoriesPage': path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'categories.js'),
        'accountsPage' : path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'accounts.js'),
        'costsPage' : path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'costs.js'),
        'regularCostsPage' : path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'regularCosts.js'),
        'statisticsPage' : path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'statistics.js'),*/
    },

    plugins: [
        new HtmlWebpackPlugin({
            template: path.join(__dirname, 'src', 'main', 'resources', 'templates', 'main.js'),
            filename: 'mainPage',
        }),

    ],
    devServer: {
        contentBase: './dist',
        compress: true,
        port: 8000,
        allowedHosts: [
            'localhost:8080'
        ]
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.css$/,
                use: [
                    'vue-style-loader',
                    'css-loader'
                ]
            },
            {
                test: /\.s(c|a)ss$/,
                use: [
                    'vue-style-loader',
                    'css-loader',
                    {
                        loader: 'sass-loader',
                        // Requires sass-loader@^7.0.0
                        options: {
                            implementation: require('sass'),
                            indentedSyntax: true // optional
                        },
                        // Requires >= sass-loader@^8.0.0
                        options: {
                            implementation: require('sass'),
                            sassOptions: {
                                indentedSyntax: true // optional
                            },
                        },
                    },
                ],
            },
        ]
    },
    plugins: [
        new VueLoaderPlugin()
    ],
    resolve: {
        modules: [
            path.join(__dirname, 'src', 'main', 'resources', 'static', 'js'),
            path.join(__dirname, 'node_modules'),
        ],
    }
}