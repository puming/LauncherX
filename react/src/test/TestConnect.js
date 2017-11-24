/**
 * Created by pm on 17-10-20.
 */
export function creatConnect({x = 0, y = 0} = {}) {
  console.log('x=' + x, 'y=' + y);

  return function connect(a, b, {i = 10, k = 20} = {}) {
    console.log('a=' + `${a}` + 'b=' + b + 'i=' + i + 'k=' + k);
  }
}

let creatConnect2 = creatConnect({x:8,y:8});
export default creatConnect2