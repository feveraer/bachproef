FROM fluent/fluentd:latest

COPY fluent.conf /fluentd/etc/

USER fluent
WORKDIR /home/fluent
ENV PATH /home/fluent/.gem/ruby/2.3.0/bin:$PATH
RUN gem install fluent-plugin-elasticsearch

EXPOSE 24224
VOLUME /fluentd/log

CMD fluentd -c /fluentd/etc/fluent.conf
