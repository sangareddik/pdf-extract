module.exports = {
  env: {
    browser: true,
    es6: true,
    node: true
  },
  extends: ['eslint:recommended', 'plugin:react/recommended'],
  // https://github.com/yannickcr/eslint-plugin-react#configuration
  settings: {
    react: {
      createClass: 'createReactClass', // Regex for Component Factory to use,
      // default to "createReactClass"
      pragma: 'React', // Pragma to use, default to "React"
      version: 'detect', // React version. "detect" automatically picks the version you have installed.
      // You can also use `16.0`, `16.3`, etc, if you want to override the detected value.
      // default to latest and warns if missing
      // It will default to "detect" in the future
      flowVersion: '0.53' // Flow version
    },
    linkComponents: [
      // Components used as alternatives to <a> for linking, eg. <Link to={ url } />
      'Hyperlink',
      { name: 'Link', linkAttribute: 'to' }
    ]
  },
  parser: 'babel-eslint',
  parserOptions: {
    ecmaFeatures: {
      arrowFunctions: true,
      jsx: true,
      experimentalObjectRestSpread: true
    },
    ecmaVersion: 2018,
    sourceType: 'module'
  },
  plugins: ['react', 'prettier'],
  rules: {
    eqeqeq: 'error',
    'no-undef': 'error',
    'no-caller': 'error',
    'no-bitwise': 'error',
    'no-cond-assign': 'error',
    'no-return-await': 'error',
    'no-duplicate-imports': 'error',
    'no-console': ['error', { allow: ['warn', 'error'] }],
    'prefer-template': 'error',
    'object-shorthand': 'error',
    'prefer-const': [
      'error',
      {
        destructuring: 'all'
      }
    ],
    'no-var': 'error',
    'max-len': [
      'error',
      {
        code: 120,
        tabWidth: 2,
        ignoreComments: true,
        ignorePattern: '^import [^,]+ from |^export | implements '
      }
    ],
    'react/prop-types': 0,
    'react/react-in-jsx-scope': 'off',
    'prettier/prettier': ['error', { singleQuote: true }]
  }
}
