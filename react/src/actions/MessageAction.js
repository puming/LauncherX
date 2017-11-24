/**
 * Created by pm on 17-10-19.
 */
import * as TYPES from './Types';
import {fetchData} from '../utils/FetchUtils';
import {createAction} from 'redux-actions';
const api = 'https://api.douban.com/v2/movie/coming_soon';

//action 是一个用于描述已发生事件的普通对象。
const soonAction = (soon) => {
  return {
    type: TYPES.FETCH_SOON_LIST,
    soon,
  };
};
export function fetchSoon(start = 0, count = 6) {
  return fetchData(start, count, soonAction)(api);
}