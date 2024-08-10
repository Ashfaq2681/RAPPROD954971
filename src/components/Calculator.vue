<template>
  <div class="calculator">
    <div class="display">{{ display || "0" }}</div>
    <div class="buttons">
      <button class="clear" @click="handleClear">C</button>
      <button class="operator" @click="handleClick('/')">÷</button>
      <button class="operator" @click="handleClick('*')">×</button>
      <button class="operator" @click="handleClick('-')">−</button>
      <button @click="handleClick('7')">7</button>
      <button @click="handleClick('8')">8</button>
      <button @click="handleClick('9')">9</button>
      <button class="operator" @click="handleClick('+')">+</button>
      <button @click="handleClick('4')">4</button>
      <button @click="handleClick('5')">5</button>
      <button @click="handleClick('6')">6</button>
      <button class="equal" @click="handleEqual">=</button>
      <button @click="handleClick('1')">1</button>
      <button @click="handleClick('2')">2</button>
      <button @click="handleClick('3')">3</button>
      <button class="zero" @click="handleClick('0')">0</button>
      <button @click="handleClick('.')">.</button>
    </div>
  </div>
</template>

<script>
import "./Calculator.css";

export default {
  data() {
    return {
      display: "",
    };
  },
  methods: {
    handleClick(value) {
      this.display += value;
    },
    handleEqual() {
      try {
        this.display = eval(this.display).toString();
      } catch (error) {
        this.display = "Error";
      }
    },
    handleClear() {
      this.display = "";
    },
  },
};
</script>
