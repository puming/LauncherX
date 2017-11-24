/**
 * Created by pm on 17-7-17.
 */
import React, {Component} from 'react';
import {
  Text,
  StyleSheet,
  View,
  Image,
  FlatList,
  TouchableOpacity,
  ActivityIndicator
} from 'react-native';
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {fetchList, fetchMore} from '../actions/HomeAction';
import Item from "./Item";
import {myconnect} from '../test';

const TAG = "Home:";
class Home extends Component {
  constructor(props) {
    super(props);
    console.log(TAG + this.props);
    myconnect();
  }

  componentDidMount() {
    let props = this.props;
    for (p in props) {
      console.log("props=" + p);
      for (pr in p.screenProps) {
        console.log("pr=" + pr);
      }
    }
    this.props.fetchListAction();
    // this.props.dispatch(fetchList(0, 6));
  }

  _onPressItem = (id) => {
    this.props.navigation.navigate('NewsDetail', {id: id});
  };
  _fetchMore = () => {
    let lastStart = this.props.json.start;
    let count = this.props.json.count;
    let start = lastStart + count;
    this.props.fetchListAction(start);
  };
  _keyExtractor = (item, index) => item.id;

  render() {
    const {navigate} = this.props.navigation;
    return (
      <View style={styles.home_container}>
        <FlatList
          data={this.props.movies}
          numColumns={3}
          refreshing={false}
          keyExtractor={this._keyExtractor}
          onEndReached={ this._fetchMore}
          ListFooterComponent={() => {
            return (<ActivityIndicator size="large"/>);

            /* refreshing &&*/
          }}
          renderItem={({item}) => {
            return (<Item
              title={item.title}
              image={item.images.medium}
              stars={item.rating.stars}
              onPress={() => navigate('Detail', {
                id: item.id,
                callback: (data) => {
                  this.setState({
                    childState: data
                  })
                }
              })
                // this._onPressItem.bind(this, item.id);
              }
            />)
          }
          }
        />
      </View>
    );
  }
}
const styles = StyleSheet.create({
  home_container: {
    flex: 1,
  },
  row: {
    paddingHorizontal: 15,
  },
  loading: {
    marginTop: 100,
  }
});
export default connect(
  state => ({
    json: state.home.json,
    movies: state.home.data,
  }),
  dispatch => bindActionCreators({fetchListAction: fetchList, fetchMore}, dispatch),
)(Home);