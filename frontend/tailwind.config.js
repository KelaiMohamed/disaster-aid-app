module.exports = {
  content: ["./src/**/*.{html,ts}"],
  theme: {
    extend: {
      colors: {
        "text-base": "#231F20",
        "text-secondary": "#646464",
        "shape-bg": "#6FB1FC",
        "bg-secondary": "#EDF1FC",
      },
    },
  },
  plugins: [require("daisyui")],
  daisyui: {
    themes: [
      {
        mytheme: {
          primary: "#3EC5FA",
          secondary: "#FDDB3A",
          accent: "#3A4256",
          neutral: "#000B41",
          "base-100": "#ffffff",
        },
      },
    ],
  },
};
