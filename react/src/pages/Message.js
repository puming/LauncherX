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
import {fetchSoon} from '../actions/MessageAction';
import MessageItem from './MessageItem';

const TAG = "Message:";
class Message extends Component {
  componentWillMount() {
    this.props.fetchSoon();
  }

  _keyExtractor = (item, index) => item.id;

  _fetchMore = () => {
    let lastStart = this.props.json.start;
    let count = this.props.json.count;
    let start = lastStart + count;
    this.props.fetchSoon(start);
  };

  render() {
    return (
      <View style={styles.container}>
        <FlatList
          data={this.props.soon}
          refreshing={false}
          keyExtractor={this._keyExtractor}
          onEndReached={ this._fetchMore}
          ListFooterComponent={() => {
            return (<ActivityIndicator size="large"/>);
          }}
          renderItem={({item}) => {
            return (
              <MessageItem
                data={item}
                onPress={() => navigate('Detail', {
                  id: item.id,
                  callback: (data) => {
                    this.setState({
                      childState: data
                    })
                  }
                })}
              />)
          }}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
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
    json:state.message.json,
    soon: state.message.data,
  }),
  dispatch => bindActionCreators({fetchSoon}, dispatch)
)(Message);