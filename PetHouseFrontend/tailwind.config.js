/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}", "./node_modules/flowbite/**/*.js"],
  theme: {
    extend: {
      colors: {
        'textDestaque' : '#7469B6',
        'textBtn': '#64ffda',
        'textSecunda': '#AD88C6',
        'bgCard': '#AD88C6',
        'subTextCard': '#a8b2d1',
        'teste': '#E1AFD1',
        'bgColor' : '#FFE6E6',
        'shadow': 'rgba(2,12,27,0.7)',
      },
      fontFamily: {
        'font-primaria': ['Roboto', 'sans-serif'],
        'font-secundaria': ['Poppins', 'sans-serif'],
      },
    },
  },
  plugins: [
    require('flowbite/plugin')
  ],
}
