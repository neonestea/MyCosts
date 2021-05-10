const path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
    entry: {
        main : path.join(__dirname, 'src', 'main', 'resources', 'js', 'main.js'),
        categories: path.join(__dirname, 'src', 'main', 'resources', 'js', 'categories.js'),
        accounts : path.join(__dirname, 'src', 'main', 'resources', 'js', 'accounts.js'),
        costs : path.join(__dirname, 'src', 'main', 'resources', 'js', 'costs.js'),
        regularCosts : path.join(__dirname, 'src', 'main', 'resources', 'js', 'regularCosts.js'),
        statistics : path.join(__dirname, 'src', 'main', 'resources', 'js', 'statistics.js'),
    },
    output: {
        path: path.resolve(__dirname, '/dist'),
        filename: '[name].js'
    },
    //entry: path.join(__dirname, 'src', 'main', 'resources', 'js', 'main.js'),
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
            path.join(__dirname, 'src', 'main', 'resources', 'js'),
            path.join(__dirname, 'node_modules'),
        ],
    }
}