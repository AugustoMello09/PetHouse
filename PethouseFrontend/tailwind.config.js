module.exports = {
  content: ["./src/**/*.{html,ts}",
    "./node_modules/flowbite/**/*.js"
  ]
  ,
  theme: {
    extend: {
      colors: {
        'textDestaque' : '#7469B6',
        'textSecunda': '#AD88C6',
        'subTextCard': '#a8b2d1',
        'bgColor': '#FFE6E6',
        'bgCard': '#AD88C6',
        'shadow': 'rgba(2,12,27,0.7)',
      },
      fontFamily: {
        'font-primaria': ['Roboto', 'sans-serif'],
        'font-secundaria': ['Poppins', 'sans-serif'],
      },
      screens: {
        'auto': {'max' : '1980px'},
        'desktop2': { 'max': '1500px' },
        'desktop': { 'max': '1280px' },
        'laptop': {'max': '1074px'},
        'tablet': { 'max': '930px' },
        'tabletMi2': {'max': '800px'},
        'tabletMin': {'max': '750px'},
        'sm4': {'max': '649px'},
        'sm5': { 'max': '545px' },  
        'sm3': { 'max': '410px' },
        'sm2': { 'max': '320px' },
        'sm1': { 'max': '360px' },
        'bbd1': { 'max': '300px' },
        'bbd2': { 'max': '250px' },
      }
    },
  },
  plugins: [
    require('flowbite/plugin')
  ],
}

